package com.sem.proj.tbike.core.cache;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class CacheManager {

	private final TaskScheduler taskScheduler;
	private final Map<String, Storage<?, ?>> storages;

	private CacheManager(TaskScheduler scheduler) {
		storages = new HashMap<>();
		taskScheduler = scheduler;
	}

	public void register(String key, Storage<?, ?> storage) {
		storages.putIfAbsent(key, storage);
	}

	public Storage<?, ?> use(String key) {
		Storage<?, ?> storage = storages.get(key);
		if (storage == null)
			throw new RuntimeException();
		return storage;
	}

	public void remove(String key) {
		storages.remove(key);
	}

	@PostConstruct
	public void clean() {
		storages.values().forEach(
				storage -> taskScheduler.scheduleAtFixedRate(
						storage::clean, storage.getMaxStorageTime()
				)
		);
	}
}
