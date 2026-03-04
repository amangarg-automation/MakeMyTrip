package Listeners;

import CommonUtilities.Dataprovider;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method method)
    {
        if (method != null && method.getParameterCount() > 0) {
            annotation.setDataProvider("dataProvider");
            annotation.setDataProviderClass(Dataprovider.class);
        }
    }
}
