package com.example.EquipmentRental.service;
import com.example.EquipmentRental.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import com.example.EquipmentRental.model.Equipment;
import org.springframework.stereotype.Service;
import com.example.EquipmentRental.repository.EquipmentRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Override
    public List<EquipmentDto> getAllEquipment() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipment -> new EquipmentDto(equipment.getId(), equipment.getModel(), equipment.getBrand(), equipment.getRentalPricePerDay(), equipment.isActive()))
                .toList();
    }

    @Override
    public Optional<EquipmentDto> getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(equipment -> new EquipmentDto(equipment.getId(), equipment.getModel(), equipment.getBrand(), equipment.getRentalPricePerDay(), equipment.isActive()));

    }

    @Override
    public EquipmentDto createEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        equipment.setBrand(equipmentDto.getBrand());
        equipment.setModel(equipmentDto.getModel());
        equipment.setRentalPricePerDay(equipmentDto.getRentalPricePerDay());
        equipment.setActive(true);

        Equipment saved = equipmentRepository.save(equipment);

        return new EquipmentDto(saved.getId(), saved.getModel(), saved.getBrand(), saved.getRentalPricePerDay(), saved.isActive());
    }

    @Override
    public void deleteEquipment(Long id) {
       equipmentRepository.deleteById(id);
    }
}