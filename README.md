# RetrofitDemo
Retrofit简单GEt，PoST，上传文件，下载使用
### 新建interface类GitService
生产各个功能的请求方法，例如初始化，登录方法
```java
@GET
Call<ReturnInfo> init(@Path("id")String id);
```
**@GET @POST @Path**等注解的使用方法请看[这里](http://www.lanyimi.com/2016/10/08/retrofit%E6%B3%A8%E8%A7%A3%E4%BD%BF%E7%94%A8/)

### 新建单列类Git
实例化Retrofit和GitService
```java
public class Git{

   private static volatile Git mInstance=new Git();
   private GitService mGitService;
   //单例模式
   public static Git getInstance(){
       if(mInstance==null){
         Git mins=mInstance;
         synchronized(Git.class){
         if(mins==null){
            mins=new Git();
            mInstance=mins;
         }
       }
     }
      return mInstance;
    }

    //Git构造方法实例化retrofit
    public Git(){
       Retrofit retrofit=new Retrofit.Builder().baseUrl(GitApi.BASE_URL)
                      .addCallAdapterFactory(RxJavaCallAdaterFactory.create())
                      .addConverterFactory(GsonConverterFactroy.create())
                      .build();
         mGitService=retrofit.create(GitService.class);

}
   //获取mGitService对像
   private GitService getGitServie(){
        return mGitServie;
     }
}

```

### 在view调用请求方法
```java
   Git.getInstance().getGitService().init("a","b").enqueue(new Callback<RetuenInfo>{

          public void onResponse(Call<ReturnInfo> call,Respinse<ReturnInfo> response){
  
                             //response.body
          }
          public void onFailure(Call<ReturnInfo> call,Throwable t){
          }

  })
```

