package se.pj.tbike.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

public final class StorageManager {

	private static final String UNREGISTERED_STORAGE =
			"unregistered storage %s";

	private final TaskScheduler taskScheduler;
	private final Map<String, Cleaner> storages;

	public StorageManager( TaskScheduler scheduler ) {
		storages = new LinkedHashMap<>();
		taskScheduler = scheduler;
	}

	public void register( String key, Storage<?, ?> storage,
	                      Trigger trigger ) {
		Supplier<ScheduledFuture<?>> task =
				() -> taskScheduler.schedule( storage::clean, trigger );
		storages.putIfAbsent( key, new Cleaner( storage, task ) );
	}

	public void register( String key, Storage<?, ?> storage,
	                      boolean cleanable ) {
		if ( !cleanable ) storages.putIfAbsent( key, new Cleaner( storage ) );
		else {
			PeriodicTrigger trigger =
					new PeriodicTrigger( storage.getMaxStorageTime() );
			trigger.setFixedRate( true );
			register( key, storage, trigger );
		}
	}

	public void register( String key, Storage<?, ?> storage ) {
		register( key, storage, true );
	}

	@SuppressWarnings( "unchecked" )
	public <K extends Comparable<K>, V> Storage<K, V> use( String key ) {
		Cleaner c = storages.get( key );
		if ( c == null )
			throw new RuntimeException(
					UNREGISTERED_STORAGE.formatted( key ) );
		if ( !c.started ) c.start();
		return (Storage<K, V>) c.storage;
	}

	public void remove( String key ) {
		Cleaner c = storages.remove( key );
		if ( c == null )
			throw new RuntimeException(
					UNREGISTERED_STORAGE.formatted( key ) );
		c.end();
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof StorageManager that ) ) return false;
		return Objects.equals( taskScheduler, that.taskScheduler ) &&
				Objects.equals( storages, that.storages );
	}

	@Override
	public int hashCode() {
		return Objects.hash( taskScheduler, storages );
	}

	@Override
	public String toString() {
		return "StorageManager" + storages;
	}

	private static class Cleaner {
		private final Storage<?, ?> storage;
		private final Supplier<ScheduledFuture<?>> task;

		private ScheduledFuture<?> future;
		private boolean started;

		public Cleaner( Storage<?, ?> storage ) {
			this.storage = storage;
			this.task = null;
		}

		public Cleaner( Storage<?, ?> storage,
		                Supplier<ScheduledFuture<?>> task ) {
			this.storage = storage;
			this.task = task;
		}

		private void start() {
			if ( cleanable() ) future = task.get();
			started = true;
		}

		private boolean isRunning() {
			if ( !cleanable() ) return started;
			return started && !future.isCancelled() && !future.isDone();
		}

		private void end() {
			if ( isRunning() && cleanable() )
				future.cancel( true );
		}

		private boolean cleanable() {
			return task != null;
		}
	}
}
