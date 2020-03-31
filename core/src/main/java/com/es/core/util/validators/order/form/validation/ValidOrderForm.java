package com.es.core.util.validators.order.form.validation;

import com.es.core.util.validators.order.form.validation.OrderFormValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = OrderFormValidation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidOrderForm {

    String message() default "Invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
