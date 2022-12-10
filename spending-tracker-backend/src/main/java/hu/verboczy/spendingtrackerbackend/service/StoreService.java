package hu.verboczy.spendingtrackerbackend.service;

import hu.verboczy.spendingtrackerbackend.controller.dto.StoreDto;
import hu.verboczy.spendingtrackerbackend.dao.entity.Store;
import hu.verboczy.spendingtrackerbackend.dao.repository.StoreRepository;
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
public class StoreService {

    StoreRepository storeRepository;

    public List<String> getStores() {
        return storeRepository.findAll().stream().map(Store::getName).collect(Collectors.toList());
    }

    public void addStore(String name) {
        if (isStoreNew(name)) {
            saveStore(name);
        } else {
            log.warn("Store [{}] already exists in the database, it won't be saved.", name);
        }
    }

    private boolean isStoreNew(String name) {
        Store store = storeRepository.findByName(name);
        return store == null;
    }

    private void saveStore(String storeName) {
        log.trace("Saving store with name [{}]...", storeName);
        Store store = new Store();
        store.setName(storeName);
        Store savedStore = storeRepository.save(store);
        log.debug("Store [{}] has been saved successfully. Generated id = [{}].", savedStore.getName(), savedStore.getId());
    }

    public void deleteStore(long id) {
        log.trace("Deleting store with id [{}]...", id);
        try {
            storeRepository.deleteById(id);
            log.debug("Store [{}] has been deleted successfully.", id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.warn("Cannot delete store with id [{}], because the given id is not saved in the database.", id);
        }
    }

    public void updateStore(StoreDto storeDto) {
        String newName = storeDto.getName();
        long id = storeDto.getId();

        log.trace("Updating store's name with id [{}] to [{}]", id, newName);

        validateUpdateRequest(storeDto);

        Optional<Store> storeById = storeRepository.findById(id);
        if (storeById.isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no store with id [%d]", id));
        } else {
            Store store = storeById.get();
            String oldName = store.getName();
            store.setName(newName);
            storeRepository.save(store);
            log.debug("Store [{}] has been renamed successfully from [{}] to [{}]", id, oldName, newName);
        }
    }

    private void validateUpdateRequest(StoreDto storeDto) {
        long id = storeDto.getId();
        if (!storeRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Won't update store, because there is no store with id [%d]", id));
        }
        if (storeRepository.existsByName(storeDto.getName())) {
            throw new IllegalArgumentException(String.format("Won't update store [%d], because a store already has name [%s].", id, storeDto.getName()));
        }
    }

}
