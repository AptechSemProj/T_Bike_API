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
	private final Map<String, CleanStorageController> storages;

	public StorageManager( TaskScheduler scheduler ) {
		storages = new LinkedHashMap<>();
		taskScheduler = scheduler;
	}

	public void register( String key, Storage<?, ?> storage,
	                      Trigger trigger ) {
		Supplier<ScheduledFuture<?>> task =
				() -> taskScheduler.schedule( storage::clean, trigger );
		storages.putIfAbsent( key,
				new CleanStorageController( storage, task ) );
	}

	public void register( String key, Storage<?, ?> storage ) {
		PeriodicTrigger trigger =
				new PeriodicTrigger( storage.getMaxStorageTime() );
		trigger.setFixedRate( true );
		register( key, storage, trigger );
	}

	@SuppressWarnings( "unchecked" )
	public <K extends Comparable<K>, V> Storage<K, V> use( String key ) {
		CleanStorageController controller = storages.get( key );
		if ( controller == null )
			throw new RuntimeException( UNREGISTERED_STORAGE.formatted( key ) );
		if ( !controller.started ) controller.start();
		return (Storage<K, V>) controller.storage;
	}

	public void remove( String key ) {
		CleanStorageController controller = storages.remove( key );
		if ( controller == null )
			throw new RuntimeException( UNREGISTERED_STORAGE.formatted( key ) );
		controller.end();
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

	private static class CleanStorageController {
		private final Storage<?, ?> storage;
		private final Supplier<ScheduledFuture<?>> task;

		private ScheduledFuture<?> future;
		private boolean started;

		public CleanStorageController( Storage<?, ?> storage,
		                               Supplier<ScheduledFuture<?>> task ) {
			this.storage = storage;
			this.task = task;
		}

		private void start() {
			future = task.get();
			started = true;
		}

		private boolean isRunning() {
			return started && !future.isCancelled() && !future.isDone();
		}

		private void end() {
			if ( isRunning() ) future.cancel( true );
		}
	}
}
