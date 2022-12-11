package hu.verboczy.spendingtrackerbackend.service;

import hu.verboczy.spendingtrackerbackend.controller.dto.SpendingTypeDto;
import hu.verboczy.spendingtrackerbackend.dao.entity.SpendingType;
import hu.verboczy.spendingtrackerbackend.dao.repository.SpendingTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class SpendingTypeService {

    SpendingTypeRepository spendingTypeRepository;

    public List<String> getSpendingTypes() {
        return spendingTypeRepository.findAll().stream().map(SpendingType::getType).collect(Collectors.toList());
    }

    public void addSpendingType(String spendingType) {
        if (spendingTypeRepository.existsByType(spendingType)) {
            log.warn("Spending type [{}] already exists in the database, it won't be saved.", spendingType);
        } else {
            saveSpendingType(spendingType);
        }
    }

    private void saveSpendingType(String type) {
        log.trace("Saving spending type with name [{}]...", type);
        SpendingType spendingType = new SpendingType();
        spendingType.setType(type);
        SpendingType savedSpendingType = spendingTypeRepository.save(spendingType);
        log.debug("Spending type [{}] has been saved successfully. Generated id = [{}].", savedSpendingType.getType(), savedSpendingType.getId());
    }

    public void deleteSpendingType(long id) {
        log.trace("Deleting spending type with id [{}]...", id);
        try {
            spendingTypeRepository.deleteById(id);
            log.debug("Spending type [{}] has been deleted successfully.", id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.warn("Cannot delete spending type with id [{}], because the given id is not saved in the database.", id);
        }
    }

    public void updateSpendingType(SpendingTypeDto spendingTypeDto) {
        String newType = spendingTypeDto.getType();
        long id = spendingTypeDto.getId();

        log.trace("Updating spending type with id [{}] to [{}]", id, newType);

        validateUpdateRequest(spendingTypeDto);

        Optional<SpendingType> spendingTypeById = spendingTypeRepository.findById(id);
        if (spendingTypeById.isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no store with id [%d]", id));
        } else {
            SpendingType spendingType = spendingTypeById.get();
            String oldType = spendingType.getType();
            spendingType.setType(newType);
            spendingTypeRepository.save(spendingType);
            log.debug("Spending type [{}] has been renamed successfully from [{}] to [{}]", id, oldType, newType);
        }
    }

    private void validateUpdateRequest(SpendingTypeDto spendingTypeDto) {
        long id = spendingTypeDto.getId();
        if (!spendingTypeRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Won't update spending type, because there is no spending type with id [%d]", id));
        }
        if (spendingTypeRepository.existsByType(spendingTypeDto.getType())) {
            throw new IllegalArgumentException(String.format("Won't update spending type [%d], because a spending type already has type [%s].", id, spendingTypeDto.getType()));
        }
    }

}
