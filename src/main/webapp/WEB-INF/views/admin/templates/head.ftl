		<header class="main-header">
		    <!-- Logo -->
		    <a href="admin" class="logo">
		      <span class="logo-mini"><b>教师</b></span>
		      <span class="logo-lg"><b>教师</b>选课系统</span>
		    </a>
		
		    <!-- Header Navbar -->
		    <nav class="navbar navbar-static-top" role="navigation">
		      <!-- Sidebar toggle button-->
		      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
		        <span class="sr-only">Toggle navigation</span>
		      </a>
		      <!-- Navbar Right Menu -->
		      <div class="navbar-custom-menu">
		        <ul class="nav navbar-nav">
		        
		          <!-- 用户栏目开始 -->
		          <li class="dropdown user user-menu">
		            <!-- Menu Toggle Button -->
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		              <!-- The user image in the navbar-->
		              <img src="${adminBase}/img/head_img.png" class="user-image" alt="User Image">
		              <!-- hidden-xs hides the username on small devices so only the image appears. -->
		              <span class="hidden-xs">${teacher.name}</span>
		            </a>
		            <ul class="dropdown-menu">
		              <!-- The user image in the menu -->
		              <li class="user-header">
		                <img src="${adminBase}/img/head_img.png" class="img-circle" alt="User Image">
		
		                <p>
		                  	${teacher.name}
		                </p>
		              </li>
		              <!-- Menu Body -->
		              <!--<li class="user-body">
		                <div class="row">
		                  <div class="col-xs-4 text-center">
		                    <a href="#">Followers</a>
		                  </div>
		                  <div class="col-xs-4 text-center">
		                    <a href="#">Sales</a>
		                  </div>
		                  <div class="col-xs-4 text-center">
		                    <a href="#">Friends</a>
		                  </div>
		                </div>
		              </li>-->
		              <!-- Menu Footer-->
		              <li class="user-footer">
		                <!-- <div class="pull-left">
		                  <a href="#" class="btn btn-default btn-flat">个人信息</a>
		                </div> -->
		                <div class="pull-right">
		                  <a href="logout" class="btn btn-default btn-flat">退出登录</a>
		                </div>
		              </li>
		            </ul>
		          </li>
		          <!-- 用户栏目结束 -->
		          <!-- Control Sidebar Toggle Button -->
		          <li>
		             
		          </li>
		        </ul>
		      </div>
		    </nav>
		  </header>
		  <!-- 左侧栏目开始 -->
		  <aside class="main-sidebar">
		
		    <!-- sidebar: style can be found in sidebar.less -->
		    <section class="sidebar">
		
		      <!-- Sidebar user panel (optional) -->
		      <div class="user-panel">
		        <div class="pull-left image">
		          <img src="${adminBase}/img/head_img.png" class="img-circle" alt="User Image">
		        </div>
		        <div class="pull-left info">
		          <p>${teacher.name}</p>
		          <!-- Status -->
		          <i class="fa fa-circle text-success"></i> Online
		        </div>
		      </div>
		
		      <!-- 搜索框开始 -->
		      <!-- <form action="#" method="get" class="sidebar-form">
		        <div class="input-group">
		          <input type="text" name="q" class="form-control" placeholder="搜索...">
		              <span class="input-group-btn">
		                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
		                </button>
		              </span>
		        </div>
		      </form> -->
		      <!-- 搜索框结束 -->
		
			<!-- 主要侧边栏目开始 -->
		      <!-- Sidebar Menu -->
		      <ul class="sidebar-menu">
		        <li class="header">普通权限</li>
		        <!-- Optionally, you can add icons to the links -->
		        <!-- <li><a href="#"><i class="fa fa-wechat"></i> <span>微信信息设置</span></a></li> -->
		        <li class="normal">
		        	<a href="teacherInfo"><i class="fa fa-user"></i> <span>教师信息管理</span></a>
		        </li>
		        <li class="normal">
		          <a href="lessonInfo"><i class="fa fa-tasks"></i> <span>课程信息管理</span></a>
		        </li>
		        <li class="normal">
		          <a href="deparmentInfo"><i class="fa fa-trophy"></i> <span>院系信息管理</span></a>
		        </li>
		         <li class="normal">
		         	<a href="planInfo"><i class="fa fa-cubes"></i> <span>教师选课管理</span></a>
		         </li>
		         <li class="normal">
		         	<a href="logInfo"><i class="fa fa-info-circle"></i> <span>日志管理</span></a>
		         </li>
		         
		      </ul>
		      <!-- 主要侧边栏目结束 -->
		    </section>
		    <!-- /.sidebar -->
		  </aside>
		  <!-- 侧边栏结束 -->