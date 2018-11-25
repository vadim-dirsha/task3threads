package ru.vadimdirsha.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import ru.vadimdirsha.java.model.People;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        //ide does not copy log4j.properties to classes folder (classpath)
        //BasicConfigurator.configure();
        /*
        TODO предварительно, клиент и коллцентр потоки которые управляют звонками, оператор и коллцентор части организации,
        возможно нужна фабрика для килентов которая задает им парметры типа предварительной длинны звонка и проч
        коллцентр управляет очередью звонков, организация управялет операторами и коллценгтром
        reorganization(reorgProt) class reorganizationProtocol.BANKROT kappa
        */
        Thread thread = new People("CrazyMan", 2000, 5000, false);
        thread.start();
        logger.info("main tread");
    }
}
