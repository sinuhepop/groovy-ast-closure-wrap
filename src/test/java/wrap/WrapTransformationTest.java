package wrap;

import java.io.File;
import java.util.Arrays;

import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.tools.ast.TransformTestHelper;
import org.junit.Test;

import wrap.WrapTransformation;

public class WrapTransformationTest {

	@Test
	public void wrapped() throws Exception {
		run("src/test/resources/test/MyClass.groovy", "wrapped", "my string");
	}

	@Test
	public void unwrapped() throws Exception {
		run("src/test/resources/test/MyClass.groovy", "unwrapped", "my string");
	}

	protected Object run(String src, String methodName, Object... args) throws Exception {
		TransformTestHelper invoker = new TransformTestHelper(new WrapTransformation(), CompilePhase.SEMANTIC_ANALYSIS);
		File filePath = new File(src);
		Class<?> clazz = invoker.parse(filePath);
		Object instance = clazz.newInstance();
		return clazz.getMethod(methodName, getTypes(args)).invoke(instance, (Object[]) args);
	}

	protected Class<?>[] getTypes(Object[] objs) {
		return Arrays.stream(objs).map(Object::getClass).toArray(Class[]::new);
	}
}
