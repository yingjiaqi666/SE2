1. 前端必须在登录状态下才能使用"update_image"接口
2. 因为url访问阿里云仓库需要权限，所以数据库通过getInformation()得到的URL是不可使用的，前端必须再使用get_image接口得到具有权限(?Expires=)的URL
3. 把pom.xml加入gitignore，任何含有账号密码的git commit都无法push