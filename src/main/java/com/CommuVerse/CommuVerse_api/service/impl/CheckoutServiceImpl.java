package com.CommuVerse.CommuVerse_api.service.impl;

import com.CommuVerse.CommuVerse_api.dto.PaymentCaptureResponse;
import com.CommuVerse.CommuVerse_api.dto.PaymentOrderResponse;
import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;
import com.CommuVerse.CommuVerse_api.integration.notification.email.dto.Mail;
import com.CommuVerse.CommuVerse_api.integration.notification.email.service.EmailService;
import com.CommuVerse.CommuVerse_api.integration.paymentPayPal.dto.OrderCaptureResponse;
import com.CommuVerse.CommuVerse_api.integration.paymentPayPal.dto.OrderResponse;
import com.CommuVerse.CommuVerse_api.integration.paymentPayPal.service.PayPalService;
import com.CommuVerse.CommuVerse_api.service.CheckoutService;
import com.CommuVerse.CommuVerse_api.repository.SubscriptionRepository;
import com.CommuVerse.CommuVerse_api.service.SubscriptionService;
import com.CommuVerse.CommuVerse_api.model.entity.Subscription;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PayPalService payPalService;
    private final SubscriptionService subscriptionService;
    private final EmailService emailService;
    private final SubscriptionRepository subscriptionRepository;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public PaymentOrderResponse createPayment(Integer subscriptionId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse =payPalService.createOrder(subscriptionId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    @Override
    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException{
        OrderCaptureResponse orderCaptureResponse = payPalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            SubscriptionDTO subscriptionDTO = subscriptionService.confirmPayment(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setSubscriptionId(subscriptionDTO.getId());

            sendSubscriptionConfirmationEmail(subscriptionDTO.getId());
        }
        return paypalCaptureResponse;
    }

    private void sendSubscriptionConfirmationEmail(Integer subId) throws MessagingException, MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Subscription subscription = subscriptionRepository.getById(subId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedEndDate = subscription.getEndAt().format(formatter);


        Map<String, Object> model = new HashMap<>();
        model.put("user", subscription.getUser().getUsername());
        model.put("total", subscription.getSubscriptionPlan().getPrice());
        model.put("endDate", formattedEndDate);

       Mail mail = emailService.createMail(
                subscription.getUser().getEmail(),
                "Confirmación de Pago de Suscripción",
                model,
                mailFrom
        );
        emailService.sendMail(mail,"email/subscription-confirmation-template");
    }
}