package testutil;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

/**
 * When an assertion fails, don't throw an exception but record the failure.
 * Calling {@code assertAll()} will cause an exception to be thrown if at least
 * one assertion failed.
 */
public class CustomSoftAssert extends Assertion {

	// LinkedHashMap to preserve the order
	private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
	private String assertMessage = null;

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);
		try {
			assertMessage = a.getMessage();
			a.doAssert();
			onAssertSuccess(a);
		} catch (AssertionError ex) {
			onAssertFailure(a, ex);
			m_errors.put(ex, a);
			System.out.println("" + ex.getMessage());
			try {
				saveScreenshot(assertMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			onAfterAssert(a);
		}
	}

	public String saveScreenshot(String assertMessage) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) TestBaseClass.driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		Random rdm = new Random();
		int num = rdm.nextInt(9999);
		String dest = System.getProperty("user.dir") + "/SaveScreenshot/" + "error" + num + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
	}

	public void assertAll() {
		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("The following asserts failed:");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append("\n\t");
				sb.append(ae.getKey().getMessage());
			}
			throw new AssertionError(sb.toString());
		}
	}

}
