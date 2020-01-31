package nl.kadaster.land_administration.domain.repository

//import nl.kadaster.land_administration.core.commons.ObjectIdentifier
//import nl.kadaster.land_administration.domain.state.model.Parcel
//import org.axonframework.commandhandling.model.Aggregate
//import org.axonframework.commandhandling.model.Repository
//import org.axonframework.eventsourcing.AggregateFactory
//import org.axonframework.eventsourcing.EventSourcingRepository
//import org.axonframework.eventsourcing.eventstore.EventStore
//import java.util.concurrent.Callable
//
//class ParcelRepository(
//        aggregateFactory: AggregateFactory<Parcel?>?, eventStore: EventStore?
//) : Repository<Parcel?> {
//    private val delegate: EventSourcingRepository<Parcel?>
//    override fun load(aggregateIdentifier: String): Aggregate<Parcel?> {
//        return this.load(aggregateIdentifier)
//    }
//
//    override fun load(
//            aggregateIdentifier: String, expectedVersion: Long
//    ): Aggregate<Parcel?> {
//        return delegate.load(aggregateIdentifier, expectedVersion)
//    }
//
//    @Throws(Exception::class)
//    override fun newInstance(factoryMethod: Callable<Parcel?>): Aggregate<Parcel?> {
//        return delegate.newInstance(factoryMethod)
//    }
//
//    fun load(aggregateIdentifier: ObjectIdentifier?): Aggregate<Parcel?> {
//        requireNotNull(aggregateIdentifier) { "Argument 'aggregateIdentifier' mag niet null zijn!" }
//        return load(aggregateIdentifier.asString())
//    }
//
//    fun add(aggregateRoot: Parcel?): Aggregate<Parcel?> {
//        return try {
//            newInstance { aggregateRoot }
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
//
//    init {
//        delegate = EventSourcingRepository<Parcel?>(aggregateFactory, eventStore)
//    }
//}
