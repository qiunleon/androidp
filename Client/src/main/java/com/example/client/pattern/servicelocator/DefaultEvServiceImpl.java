package com.example.client.pattern.servicelocator;

/**
 * Created by alienware on 2018/1/30.
 */

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
