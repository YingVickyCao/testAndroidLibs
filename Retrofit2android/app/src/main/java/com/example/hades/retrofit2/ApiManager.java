package com.example.hades.retrofit2;

public class ApiManager {

//    Context context;
//    public static final String BASE_URL = "https://api.github.com/";
//
//
//    private OkHttpClient okHttpClient;
//    private Authenticator authenticator = new Authenticator() {
//        @Override
//        public Request authenticate(Route route, Response response) {
//            return null;
//        }
//    };
//
//
//    private ApiManager() {
//    }
//
//    public void setAuthenticator(Authenticator authenticator) {
//        this.authenticator = authenticator;
//    }
//
//
//    public static class Builder {
//        String email, password;
//        ApiManager apiManager = new ApiManager();
//
//        public Builder setAuthenticator(Authenticator authenticator) {
//            apiManager.setAuthenticator(authenticator);
//            return this;
//        }
//
//        public ApiManager build(String param_email, String param_password) {
//            this.email = param_email;
//            this.password = param_password;
//            return apiManager.newInstance(email, password);
//        }
//
//    }
//
//    public class RequestTokenInterceptor implements Interceptor {
//        String email, password;
//        String credentials, basic;
//        public RequestTokenInterceptor(String email, String password) {
//            this.email = email;
//            this.password = password;
//            credentials = email + ":" + password;
//            basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//        }
//
//        @Override
//        public Response intercept(Interceptor.Chain chain) throws IOException {
//            Request original = chain.request();
//            Request.Builder builder = original.newBuilder()
//                    .addHeader("Authorization", basic)
//                    .method(original.method(), original.body());
//            return chain.proceed(builder.build());
//        }
//    }
//
//    private ApiManager newInstance(String email, String password) {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.i("http", message);
//            }
//        });
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//
//        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .addInterceptor(new RequestTokenInterceptor(email, password))
//                .authenticator(authenticator)
//                .build();
//
//
//        return this;
//    }
//
//
//    public <T> T createRest(Class<T> t) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        return retrofit.create(t);
//    }

}
