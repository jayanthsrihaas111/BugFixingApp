import React from 'react';
import './Dashboard.scss';
import cookie from 'react-cookies';

import '@fortawesome/fontawesome-free/css/all.min.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Question from '../Dashboard/Question/Question.jsx';

import {
	MDBBtn,
	MDBIcon,
	// MDBCard,
	// MDBCardBody,
	// MDBCardImage,
	// MDBCardTitle,
	// MDBCardText,
	// MDBRow,
	MDBCol,
	// MDBIcon
} from 'mdbreact';

class Dashboard extends React.Component {

	state = {
		'search': "",
		'questions': null
	}

	componentDidMount() {
		var username = cookie.load('username');
		if (username === undefined) {
			window.location.href = '/signin';
		} else {
			this.setState({ 'username': username });
			var user_id = cookie.load('user_id');
			this.setState({ 'user_id': user_id });
		}

		let self = this;

		const chunks = [];
		var result = " not updated";
		var base_url = cookie.load('base_url');

		fetch(`${base_url}/dashboard`, {
			method: 'POST',
			headers: {
				
			},
			body: ""
		}).then(function (response) {

			const reader = response.body.getReader();

			reader.read().then(({ value, done }) => {
				var string = new TextDecoder("utf-8").decode(value);
				chunks.push(string);
			}).then(() => {
				// console.log(chunks[0]);
				result = JSON.parse(chunks[0]);
			}).then(() => {
				self.setState({ questions: result });
				self.setState({
					'filteredQuestions': result
				});
				console.log(self.state);
			}
			);
		})

	}

	onSearchInputChange = (e) => {
		this.setState({ 'search': e.target.value });
		
		if (this.state.search.trim().length == 0) {
			this.setState({
				'filteredQuestions': this.state.questions
			})
		}
		else {
			this.setState({
				'filteredQuestions': this.state.questions.filter(ques => {
					return ques.Question_Title.toLowerCase().includes(this.state.search.toLowerCase())
				})
			})
		}

		console.log(this.state.filteredQuestions);
	}

	handleSearch = () => {
		if (this.state.search === "") {
			alert("Please Enter Something to search!!")
		}
	}

	render() {
		var flag = 0;
		return (
			<div className="dashboard">

				{this.state.username !== undefined ?
					<div>
						<div>

							<h1> Questions </h1>



							<MDBCol md="11" className="searchbar">
								<div className="input-group md-form form-sm form-1 pl-0">
									<div className="input-group-prepend">
										<span className="input-group-text purple lighten-3" id="basic-text1">
											<MDBIcon className="text-white" icon="search" />
										</span>
									</div>
									<input className="form-control my-0 py-1" type="text" placeholder="Search"
										aria-label="Search" onChange={this.onSearchInputChange} />
									<MDBBtn color="unique" rounded size="md" className="mr-auto searchbutton"
										onClick={this.handleSearch}>
										Search
                                    </MDBBtn>
								</div>

							</MDBCol>


							<FormControlLabel className="headeritem"
								control={
									<Checkbox
										checked={this.state.checkedB}
										onChange={() => {
											this.setState({ checkedB: !this.state.checkedB })
										}}
										name="checkedB"
										color="Secondary"
									/>
								}
							/>

							<h4 className="headeritem"> Your Questions</h4>
						</div>

						{
							this.state.filteredQuestions == null ?
								<h1>Component loading wait</h1> :
								this.state.filteredQuestions.map(ques => {
									return <div>
										{
											(!(this.state.checkedB == true) || this.state.user_id == ques.Creator_id) ?
												<Question Ques_id={ques.Ques_id} Question_Title={ques.Question_Title} Creator_id={ques.Creator_id} Viewcount={ques.Viewcount} Answer_count={ques.Answer_count} Votes={ques.Votes} Tags={ques.Tags.split(',')} flag={[flag++] % 4} />
												: null
										}
									</div>

								})
						}

						<br></br>
						<br></br>
					</div>

					: <div>
						<h1>Please Login!!</h1>
					</div>
				}
			</div>
		);
	}

}

export default Dashboard;
