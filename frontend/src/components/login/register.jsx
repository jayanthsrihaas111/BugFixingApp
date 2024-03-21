import React, { Component } from 'react';
import cookie from 'react-cookies';
// import { withRouter } from 'react-router-dom'

// import './Register.css';
export class Register extends Component {

	componentWillMount() {
		this.setState({ isRegisterSuccess: false })
		console.log(this.state);
	}

	state = {
		'username': '',
		'email': '',
		'rollNo': '',
		'date': '',
		'password': '',
		'wrongUsernameFormat': false,
		'wrongEmailFormat': false,
		'wrongPasswordFormat': 0,
		'wrongRollNoFormat': 0,
	}

	handleUsernameChange = (e) => {
		this.setState({ 'username': e.target.value })
	}
	handlePasswordChange = (e) => {
		this.setState({ 'password': e.target.value })
	}
	handleRollNoChange = (e) => {
		this.setState({ 'rollNo': e.target.value })
	}
	handleEmailChange = (e) => {
		this.setState({ 'email': e.target.value })
	}
	handleDateChange = (e) => {
		this.setState({ 'date': e.target.value });
		console.log("Date Changed");
	}


	checkRegex = () => {
		var flag = 1;

		this.setState(
			{
				'wrongUsernameFormat': 0,
				'wrongEmailFormat': 0,
				'wrongPasswordFormat': 0,
				'wrongRollNoFormat': 0,
			}
		)


		if (this.state.username.length <= 5 || this.state.username.length >= 15) {
			this.setState({ 'wrongUsernameFormat': 1 })
			flag = 0;
		}

		var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+.+[a-zA-Z-])*$/;
		if (this.state.email === "" || !(this.state.email.match(mailformat))) {
			this.setState({ 'wrongEmailFormat': 1 })
			flag = 0;
		}
		var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
		if (this.state.password === "" || !(this.state.password.match(passw))) {
			this.setState({ 'wrongPasswordFormat': 1 })
			flag = 0;
		}
		if (this.state.rollNo.length <= 5 || this.state.rollNo.length >= 25) {
			this.setState({ 'wrongRollNoFormat': 1 })
			flag = 0;
		}
		if (this.state.date === "") {
			this.setState({ 'wrongDateFormat': 1 })
			flag = 0;
		}

		return flag;
		// if (flag) {
		// 	alert("User Registration done");
		// }

	}

	goToHomePage = () => {
		window.location.href = window.location.origin + '/'
	}


	

	handleRegister = () => {
		// console.log(this.state);

		var tobesent = {
			"username": this.state.username,
			"password": this.state.password,
			"email": this.state.email,
			"dob": this.state.date,
			"user_id": this.state.rollNo
		}
		console.log(tobesent);
		var base_url = cookie.load('base_url');
		const chunks = [];
		fetch(`${base_url}/register`, {
			method: 'POST',
			headers: {
				"Content-Type": "application/json",
				"Access-Control-Allow-Headers": "*",
				"Access-Control-Allow-Origin": "*",
				"Access-Control-Allow-Methods": "POST,GET",
			},
			body: JSON.stringify(tobesent)
		}).then(function (response) {
			const reader = response.body.getReader();

			reader.read().then(({ value, done }) => {
				var string = new TextDecoder("utf-8").decode(value);
				chunks.push(string);
			}).then(() => {

				var result = JSON.parse(chunks[0]);
				
				if(result['userexists'] === "1"){
					alert("User already exists!")
					console.log(result);
					
				}
				else{
					window.location.href = '/signin';
				}
				
			});
		})

	}

	render() {

		const Button = () => (
			<button
				type="button" className="btn mybtn"
				id="registerButton"
				value="Register"
				onClick={
					(e) => {
						if (this.checkRegex()) {
							this.handleRegister()
						} else {
							console.log("Hello ", this.state);
						}
					}
				}
			>
				Register
			</button>
		)


		return (
			<div className="base-container register">
				<br></br>
				<br></br>
				<h3>Register</h3>
				<div className="content">

					<div className="form">
						<div className="form-group">
							<label for="username">Username</label>
							<input type="username" id="username" placeholder="Enter your Username"
								onChange={this.handleUsernameChange} />
						</div>

						{
							this.state.wrongUsernameFormat ?
								<p className="errormsg">Wrong Username Format</p>
								: null
						}

						<div className="form-group">
							<label htmlFor="date">Date of Birth</label>
							<input type="date" id="date" placeholder="Enter your DOB" onChange={this.handleDateChange} />
						</div>

						{
							this.state.wrongDateFormat ?
								<p className="errormsg">Please Enter Complete Date of Birth</p>
								: null
						}

						<div className="form-group">
							<label htmlFor="email">Email</label>
							<input type="email" id="email" placeholder="Enter your Email"
								onChange={this.handleEmailChange} />
						</div>

						{
							this.state.wrongEmailFormat ?
								<p className="errormsg">Wrong Email Format</p>
								: null
						}


						<div className="form-group">
							<label for="rollNo">Register Number</label>
							<input type="text" id="rollNo" placeholder="Enter your Roll No"
								onChange={this.handleRollNoChange} />
						</div>

						{
							this.state.wrongRollNoFormat ?
								<p className="errormsg">Wrong Register No. Format</p>
								: null
						}


						<div className="form-group">
							<label for="password">Password</label>
							<input type="password" id="password" placeholder="Enter your Password"
								onChange={this.handlePasswordChange} />
						</div>

						{
							this.state.wrongPasswordFormat ?
								<p className="errormsg">Password specs: >5 chars with atleast 1 small, 1capital, 1 num,                                 1 spec. char </p>
								: null
						}



						<Button />

					</div>

				</div>


			</div>

		);
	}
}

export default Register;