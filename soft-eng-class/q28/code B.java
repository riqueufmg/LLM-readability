public void writeReport()
{
	Logger logger = Logger.getLogger(JarScan.class.getName());

	String report = operation.getReport();

	logger.info(report);
}