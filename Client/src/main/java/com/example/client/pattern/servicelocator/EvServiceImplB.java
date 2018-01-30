package com.example.client.pattern.servicelocator;

@Provider(name = "serviceImplb")
public class EvServiceImplB extends DefaultEvServiceImpl {
    @Override
    public void execute() {
        System.out.println(this.getClass().getSimpleName() + " execute.");
    }
}
