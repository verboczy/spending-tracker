package hu.verboczy.spendingtrackerbackend.controller;

import hu.verboczy.spendingtrackerbackend.controller.dto.SpendingTypeDto;
import hu.verboczy.spendingtrackerbackend.service.SpendingTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/spendingType")
public class SpendingTypeController {
    
    SpendingTypeService spendingTypeService;

    @PostMapping
    public void addSpendingType(@RequestBody String spendingType) {
        log.info("Request: addSpendingType(name=[{}])", spendingType);
        spendingTypeService.addSpendingType(spendingType);
    }

    @PutMapping
    public void updateSpendingType(@RequestBody SpendingTypeDto spendingType) {
        log.info("Request: updateSpendingType(id=[{}], newName=[{}])", spendingType.getId(), spendingType.getType());
        try {
            spendingTypeService.updateSpendingType(spendingType);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.warn(illegalArgumentException.getMessage());
        }
    }

    @GetMapping("/list")
    public List<String> getSpendingTypes() {
        log.info("Request: getSpendingTypes()");
        List<String> spendingTypes = spendingTypeService.getSpendingTypes();
        log.info("Response: getSpendingTypes() = [{}]", spendingTypes);
        return spendingTypes;
    }

    @DeleteMapping("/delete")
    public void deleteSpendingType(@RequestParam long id) {
        log.info("Request: deleteSpendingType(id=[{}])", id);
        spendingTypeService.deleteSpendingType(id);
    }
}
