public void calculate(String path, CKNotifier notifier) {
	String[] javaFiles = FileUtils.getAllJavaFiles(path);
	log.info("Found " + javaFiles.length + " java files");

	Path basePath = Paths.get(path);
	calculate(basePath, notifier,
	 	Stream.of(javaFiles)
			.map(basePath::resolve)
			.toArray(Path[]::new)
		);
}
