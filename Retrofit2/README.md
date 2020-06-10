# Retrofit2

# 1 Usage

## 1.1 Get Method

```
node node_js_get.js
```

```java
// http://localhost:7777/getSum?num1=5&num2=15
@GET("getSum")
Call<Integer> getSum(@Query("num1") int num1, @Query("num2") int num2);
```
