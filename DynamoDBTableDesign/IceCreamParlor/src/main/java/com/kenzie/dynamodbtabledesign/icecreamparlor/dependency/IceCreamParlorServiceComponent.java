package com.kenzie.dynamodbtabledesign.icecreamparlor.dependency;

import com.kenzie.dynamodbtabledesign.icecreamparlor.IceCreamParlorService;

import dagger.Component;

@Component
public interface IceCreamParlorServiceComponent {
    IceCreamParlorService provideIceCreamParlorService();
}
