@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigAdapter{

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
         http.authorizeRequests()
             .antMatcher("/").access("hasRole(reader)")
             .antMatcher("/**").permitAll()

             .and()
              //委托登陆
             .formLogin()
               .loginPage("/login")
               .failureUrl("/login?error=true");
    }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.userDetailsService(new userDetailsService(){
             @Override
             public UserDetails loadUserByUsername(String username) throws UserNotFoundException{
               return readerRepository.findOne(username);
             }


       });
   }


}
