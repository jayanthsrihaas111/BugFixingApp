import React from "react";
import loginImg from "./login.svg";
import cookie from 'react-cookies';


export class Login extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			username: "",
			password: ""
		}
	}

	onUsernameChanged = (e) => {
		this.setState({ username: e.target.value })
		// console.log(this.state.username);
	}

	onPasswordChanged = (e) => {
		this.setState({ password: e.target.value })
	}

	verifyLogin = (username, password) => {

	


		// var db = [
		// 	{
		// 		'username': "psp",
		// 		'password': "1919"
		// 	},
		// 	{
		// 		'username': "sachmo",
		// 		'password': "sachipo"
		// 	}
		// ]

		// for (var i in db) {
		// 	if (db[i]['username'] === username && db[i]['password'] === password) return true;
		// }

		var base_url = cookie.load('base_url');

		var tobesent = {
			"username": username,
			"password": password
		}

		const chunks = [];
		fetch(`${base_url}/login`, {
			method: 'POST',
			headers: {
				
			},
			body: JSON.stringify(tobesent)
		}).then(function (response) {
			const reader = response.body.getReader();

			reader.read().then(({ value, done }) => {
				var string = new TextDecoder("utf-8").decode(value);
				chunks.push(string);
			}).then(() => {

				var result = JSON.parse(chunks[0]);
				// console.log(chunks);
				if (result['userstatus'] === "1") {
					console.log(result);
					cookie.save('username', username);
					cookie.save('user_id', result['user_id']);
					if (result['mentor_role'] === 't') {
						console.log("U r mentor");
						cookie.save('mentor_role', true);
					}
					else {
						cookie.save('mentor_role', false);
					}
					window.location.href = '/dashboard';
				}
				else {
					alert("Retry Logging In!!");
				}
			});
		})

	}

	handleLogin = () => {
		this.verifyLogin(this.state.username, this.state.password);
	}

	render() {
		return (
			<div className="base-container" ref={this.props.containerRef}>
				<div className="header">Login</div>
				<div className="content">
					<div className="form">
						<div className="image">
							<img src={loginImg} className="regimg" alt="" />
						</div>
						<br></br>

						<div className="form-group">
							<label htmlFor="regno">Register Number</label>
							<input type="text" name="regno" placeholder="register number"
								onChange={this.onUsernameChanged} />
						</div>
						<div className="form-group">
							<label htmlFor="password">Password</label>
							<input type="password" name="password" placeholder="password"
								onChange={this.onPasswordChanged} />
						</div>
					</div>
				</div>
				<div className="footer">
					<button type="button" className="btn" onClick={this.handleLogin}>
						Login
                    </button>
				</div>
			</div>
		);
	}
}
