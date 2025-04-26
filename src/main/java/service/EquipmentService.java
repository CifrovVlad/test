package service;

import dto.EquipmentDto;
import model.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    List<EquipmentDto> getAllEquipment();
    Optional<EquipmentDto> getEquipmentById(Long id);
    EquipmentDto createEquipment(EquipmentDto equipmentDto);
    EquipmentDto updateEquipment(Long id, EquipmentDto equipmentDto);
    void deleteEquipment(Long id);
}
