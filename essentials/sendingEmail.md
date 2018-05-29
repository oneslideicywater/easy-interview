# Spring Email Sending

Spring邮件的接口是$MailSender$

配置一个$MailSenderImpl$:

    @Bean
    public  MailSender mailSender(Environment env){
      JavaMailSenderImple mailSender=new JavaMailSenderimpl();
      mailSender.setHost(env.getProperty("mailserver.host"));
      mailSender.setPort(env.getProperty("mailserver.port"));
      mailSender.setUsername(env.getProperty("mailserver.username"));
      mailSender,setPassword(env.getProperty("mailserver.password"));
      return mailSender;
    }

上述代码可以自动配置自己的$MailSession$

#fetchJNDI
       
       
       @Bean
        public JndiObjectFactoryBean mailSession(){
            JndiObjectFactoryBean jndi=new JdniObjectFactoryBean();
            jndi.setJndiName("mail/Session");
            jndi.setProxyInterface(MailSession.class);
             jndi.setResourceRef(true);
            return jndi;
            }

>接下来，你可以去设置MailSenderBean了

     @Bean
     public MailSender mailSender(MailSession session){
       JavaMailSenderImple mailSender=new JavamailSenderImpl();
       mailSender.setSession(session);
       return mailSender;

     }
