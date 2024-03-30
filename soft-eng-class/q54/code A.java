void doService(final ServletRequest servletRequest, ServletResponse servletResponse)
    throws ServletException, IOException {

  HttpServletRequest request =
      new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
        private boolean pathComputed;
        private String path;

        private boolean pathInfoComputed;
        private String pathInfo;

        @Override
        public String getPathInfo() {
          if (!isPathInfoComputed()) {
            String servletPath = getServletPath();
            int servletPathLength = servletPath.length();
            String requestUri = getRequestURI();
            String contextPath = getContextPath();
            // https://github.com/google/guice/issues/1655, contextPath is occasionally null
            int contextPathLength = contextPath != null ? contextPath.length() : 0;
            pathInfo = requestUri.substring(contextPathLength).replaceAll("[/]{2,}", "/");
            // See: https://github.com/google/guice/issues/372
            if (pathInfo.startsWith(servletPath)) {
              pathInfo = pathInfo.substring(servletPathLength);
              // Corner case: when servlet path & request path match exactly
              // (without trailing '/'), then pathinfo is null.
              if (pathInfo.isEmpty() && servletPathLength > 0) {
                pathInfo = null;
              } else {
                try {
                  pathInfo = new URI(pathInfo).getPath();
                } catch (URISyntaxException e) {
                  // ugh, just leave it alone then
                }
              }
            } else {
              pathInfo = null; // we know nothing additional about the URI.
            }
            pathInfoComputed = true;
          }

          return pathInfo;
        }

        // NOTE(user): These two are a bit of a hack to help ensure that request dispatcher-sent
        // requests don't use the same path info that was memoized for the original request.
        // NOTE(user): I don't think this is possible, since the dispatcher-sent request would
        // perform its own wrapping.
        private boolean isPathInfoComputed() {
          return pathInfoComputed
              && servletRequest.getAttribute(REQUEST_DISPATCHER_REQUEST) == null;
        }

        private boolean isPathComputed() {
          return pathComputed && servletRequest.getAttribute(REQUEST_DISPATCHER_REQUEST) == null;
        }

        @Override
        public String getServletPath() {
          return computePath();
        }

        @Override
        public String getPathTranslated() {
          final String info = getPathInfo();

          return (null == info) ? null : getServletContext().getRealPath(info);
        }

        // Memoizer pattern.
        private String computePath() {
          if (!isPathComputed()) {
            String servletPath = super.getServletPath();
            path = patternMatcher.extractPath(servletPath);
            pathComputed = true;

            if (null == path) {
              path = servletPath;
            }
          }

          return path;
        }
      };

  doServiceImpl(request, (HttpServletResponse) servletResponse);
}