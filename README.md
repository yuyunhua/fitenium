# fitenium 

## 使用方法
1. 设置好selenium运行环境，具体方法可以本地，目前只支持本地运行，后续可以支持selenium grid
2. 将编译好的包，放到fitnesse根目录的FitNesseRoot/data/下面，如果没有data目录，新建一个
3. 在fitnesse上编写测试脚本（在百度搜索并尝试登录的示例）
    ```
    !define TEST_SYSTEM {slim} 
    !path FitNesseRoot/data/*
    !path FitNesseRoot/data/
    
    |Library    |
    |StepRunner |
    
    |StepRunner|
    | target          | operation | value                     | run? |
    | browser         | open      | !-http://www.baidu.com/-! | OK   |
    | 百度一下         | type      | selendroid                |      |
    | 百度一下         | click     |                           |      |
    | 登录             | click     |                           |      |
    | 手机/邮箱/用户名  | type      | hello                     |      |
    | 密码             | type      | world                     |      |
    | 登录             | click     |                           |      |
    ```
4. 运行
5. 截图
    ![运行截图](screenshot.png)