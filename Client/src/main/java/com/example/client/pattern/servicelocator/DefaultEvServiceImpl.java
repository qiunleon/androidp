package com.example.client.pattern.servicelocator;

@Provider(name = "default")
public class DefaultEvServiceImpl implements EvService {

    public DefaultEvServiceImpl() {
        Provider provider = this.getClass().getAnnotation(Provider.class);
        String code = provider.name();
        AvServiceLocator.getInstance().register(code, this);
    }

    @Override
    public void execute() {
        System.out.println(this.getClass().getSimpleName() + "execute.");
    }
}
