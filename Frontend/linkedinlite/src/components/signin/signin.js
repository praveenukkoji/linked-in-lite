import api from '../../services/user';
import './signin.css';

function SignIn() {
    function login(event){
        event.preventDefault();
        let email = event.target.username.value;
        let password = event.target.password.value;

        if(email === "" || password === "") {
            alert("signin: invalid credentials");
            return;
        }

        api.loginUser(email, password).then(response => {
            localStorage.setItem("userId", response.data.payload);
            alert("signin: " + response.data.message);
        })
    }

	return (
		<div className='signin-root'>
			<form className="signin-form" onSubmit={login}>
                <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input type="email" className="form-control" id="username" aria-describedby="emailHelp" required/>
                    <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
                </div>
                <div className="mb-3">
                    <label className="form-label">Password</label>
                    <input type="password" className="form-control" id="password" autoComplete="on" required/>
                </div>
                <div className="btn-align-center">
                    <button type="submit" className="btn btn-primary">Sign In</button>
                </div>
            </form>
		</div>
	);
}

export default SignIn;
