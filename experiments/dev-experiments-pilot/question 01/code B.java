public void calculate(String path, CKNotifier notifier) {
	String[] javaFiles = FileUtils.getAllJavaFiles(path);
	log.info("Found " + javaFiles.length + " java files");

	calculate(Paths.get(path), notifier,
	 	Stream.of(javaFiles)
			.map(Paths::get)
			.toArray(Path[]::new)
		);
}
