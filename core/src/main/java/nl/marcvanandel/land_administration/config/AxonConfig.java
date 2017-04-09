/*
 * Copyright (c) 2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.marcvanandel.land_administration.config;

import nl.marcvanandel.land_administration.command.ParcelCommandHandler;
import nl.marcvanandel.land_administration.domain.model.Parcel;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAxon
public class AxonConfig {

    @Autowired
    private AxonConfiguration axonConfiguration;
    @Autowired
    private EventBus eventBus;

    @Bean
    public ParcelCommandHandler parcelCommandHandler() {
        return new ParcelCommandHandler(axonConfiguration.repository(Parcel.class), eventBus);
    }
    
}
