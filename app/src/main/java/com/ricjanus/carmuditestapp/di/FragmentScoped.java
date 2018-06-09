package com.ricjanus.carmuditestapp.di;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface FragmentScoped {
}