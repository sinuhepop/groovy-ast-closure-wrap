package wrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;
import org.codehaus.groovy.transform.sc.StaticCompileTransformation;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@GroovyASTTransformationClass(classes = { WrapTransformation.class, StaticCompileTransformation.class })
public @interface Wrap {
}
