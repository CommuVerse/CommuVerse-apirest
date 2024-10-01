package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionPlanDTO;
import com.CommuVerse.CommuVerse_api.mapper.SubscriptionPlanMapper;
import com.CommuVerse.CommuVerse_api.model.entity.SubscriptionPlan;
import com.CommuVerse.CommuVerse_api.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;

    // Método para crear un plan de suscripción
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO dto) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toEntity(dto);
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return subscriptionPlanMapper.toDTO(savedPlan);
    }

    // Método para obtener todos los planes de suscripción
    public List<SubscriptionPlanDTO> getAllSubscriptionPlans() {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();
        return plans.stream()
                .map(subscriptionPlanMapper::toDTO) // Convertir cada entidad en DTO
                .collect(Collectors.toList());
    }

    // Método para obtener todos los planes de suscripción asociados con un ID de usuario
    public List<SubscriptionPlanDTO> getAllSubscriptionPlansByUserId(Integer userId) {
        // Obtener todos los planes de suscripción por el ID del creador
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findByCreator_Id(userId);
        return plans.stream()
                    .map(subscriptionPlanMapper::toDTO) // Convertir cada entidad en DTO
                    .collect(Collectors.toList());
    }

    public SubscriptionPlanDTO modifySubscriptionPlan(Integer id, SubscriptionPlanDTO dto) {
        Optional<SubscriptionPlan> existingPlanOpt = subscriptionPlanRepository.findById(id);
        if (existingPlanOpt.isPresent()) {
            SubscriptionPlan existingPlan = existingPlanOpt.get();
            
            // Actualizar los campos
            existingPlan.setName(dto.getName());
            existingPlan.setDescription(dto.getDescription());
            existingPlan.setPrice(dto.getPrice());
            existingPlan.setRenewalPeriod(dto.getRenewalPeriod());
            existingPlan.setLevel(dto.getLevel());
            
            // Guardar el plan actualizado
            SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);
            return subscriptionPlanMapper.toDTO(updatedPlan);
        }
        return null; // Retorna null si no se encuentra el plan
    }

        // Método para eliminar un plan de suscripción
        public boolean deleteSubscriptionPlan(Integer id) {
            if (subscriptionPlanRepository.existsById(id)) {
                subscriptionPlanRepository.deleteById(id);
                return true; // Indica que se eliminó
            }
            return false; // Indica que no se encontró el plan
        }
    
}
