package hu.verboczy.spendingtrackerbackend.controller;

import hu.verboczy.spendingtrackerbackend.controller.dto.StoreDto;
import hu.verboczy.spendingtrackerbackend.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/store")
public class StoreController {

    StoreService storeService;

    @PostMapping
    public void addStore(@RequestBody String name) {
        log.info("Request: addStore(name=[{}])", name);
        storeService.addStore(name);
    }

    @PutMapping
    public void updateStore(@RequestBody StoreDto store) {
        log.info("Request: updateStore(id=[{}], newName=[{}])", store.getId(), store.getName());
        try {
            storeService.updateStore(store);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.warn(illegalArgumentException.getMessage());
        }
    }

    @GetMapping("/list")
    public List<String> getStores() {
        log.info("Request: getStores()");
        List<String> stores = storeService.getStores();
        log.info("Response: getStores() = [{}]", stores);
        return stores;
    }

    @DeleteMapping("/delete")
    public void deleteStore(@RequestParam long id) {
        log.info("Request: deleteStore(id=[{}])", id);
        storeService.deleteStore(id);
    }
}
