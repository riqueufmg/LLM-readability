public void writeReport()
{
	Writer writer = new PrintWriter(System.out);

	String report = operation.getReport();

	try
	{
		writer.write(report);
		writer.flush();
		writer.close();
	}
	catch (IOException e)
	{
		e.printStackTrace();
	}
}