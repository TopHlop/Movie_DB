package com.example.themoviedb.di.component;

import com.example.themoviedb.di.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {
}
