package nl.kadaster.land_administration


//@Configuration
//open class ApplicationConfig : WebMvcConfigurerAdapter()
//
//@Configuration
//@EnableAxon
//open class AxonConfig {
//    @Autowired
//    private val axonConfiguration: AxonConfiguration? = null
//    @Autowired
//    private val eventBus: EventBus? = null
//
//    @Bean
//    open fun parcelCommandHandler(): ParcelCommandHandler {
//        return ParcelCommandHandler(axonConfiguration!!.repository<Parcel>(Parcel::class.java), eventBus)
//    }
//}
//
//@Configuration
//@ComponentScan("nl.kadaster.land_administration.command")
//open class CommandHandlerConfiguration {
//    @Bean
//    open fun commandBus(): CommandBus {
//        val simpleCommandBus = SimpleCommandBus()
//        simpleCommandBus.registerDispatchInterceptor(BeanValidationInterceptor())
//        return simpleCommandBus
//    }
//
//    @Bean
//    open fun commandGateway(commandBus: CommandBus?): CommandGateway {
//        return DefaultCommandGateway(commandBus)
//    }
//
//    @Bean
//    open fun eventStorageEngine(): EventStorageEngine {
//        return InMemoryEventStorageEngine()
//    }
//}
//
//class RepositoryConfig {
//    @Bean
//    fun parcelRepository(
//            kadastraalObjectAggregateFactory: AggregateFactory<Parcel?>?,
//            koersRegistratieEventStore: EventStore?
//    ): ParcelRepository {
//        return ParcelRepository(
//                kadastraalObjectAggregateFactory,
//                koersRegistratieEventStore)
//    }
//}
