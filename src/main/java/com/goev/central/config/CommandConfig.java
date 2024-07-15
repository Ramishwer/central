package com.goev.central.config;


import com.goev.central.command.targets.CentralTarget;
import com.goev.central.command.targets.PartnerTarget;
import com.goev.lib.command.service.CommandProcessor;
import com.goev.lib.command.service.impl.SimpleCommandProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class CommandConfig {

    @Bean
    public CommandProcessor getCommandProcessor() {
        SimpleCommandProcessor commandProcessor = new SimpleCommandProcessor();
        commandProcessor.registerTargets(PartnerTarget.getTarget(commandProcessor));
        commandProcessor.registerTargets(CentralTarget.getTarget(commandProcessor));
        return commandProcessor;
    }

}
