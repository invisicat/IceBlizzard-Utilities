package dev.blizzardutils.string;

import dev.blizzardutils.task.kronos.KronosLibrary;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Test {

    static KronosLibrary<Void> kronosLibrary;

    public static void main(String[] args){
        kronosLibrary = new KronosLibrary<>();
        kronosLibrary.createNewChain(null).runAsync(() -> System.out.println("Accepting async")).whenCompleteSync((s, throwable) -> System.out.println("This is a test"))
        .acceptAsync(s -> System.out.println("Accepting async "));
    }
}
