package com.example.client.pattern.servicelocator;

@Provider(name = "serviceImpla")
public class EvServiceImplA extends DefaultEvServiceImpl {
    @Override
    public void execute() {
        System.out.println(this.getClass().getSimpleName() + " execute.");
    }
}
