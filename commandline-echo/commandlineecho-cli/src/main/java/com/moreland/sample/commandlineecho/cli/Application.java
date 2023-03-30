package com.moreland.sample.commandlineecho.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.moreland.sample.commandlineecho")
public class Application implements CommandLineRunner  {

	private final Printer printer;
	private final com.moreland.sample.commandlineecho.service.Obfuscator obfuscator;

	@Autowired
	public Application(Printer printer, com.moreland.sample.commandlineecho.service.Obfuscator obfuscator) {
		this.printer = printer;
		this.obfuscator = obfuscator;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @SuppressWarnings({"java:S106"})
    @Override
    public void run(String... args) throws Exception {

		for (var arg : args) {
			printer.writeline(obfuscator.jumble(arg));
		}

	}

}
