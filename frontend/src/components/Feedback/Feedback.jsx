import React from 'react';
import './Feedback.scss';

import { Form, Button } from 'react-bootstrap';
import { MDBContainer, MDBRating } from 'mdbreact';

import ReactStars from "react-rating-stars-component";

import Chart from './Chart/Chart';
import cookie from 'react-cookies';



class Feedback extends React.Component {


	componentDidMount = () => {

		var base_url = cookie.load('base_url');

		var user_id = cookie.load('user_id');
		if (user_id === undefined) {
			window.location.href = '/signin';
		} else {
			var user_id = cookie.load('user_id');
			this.setState({ 'user_id': user_id });
		}


		var tobesent = {
			"user_id": cookie.load('user_id')
		}

		let self = this;

		const chunks = [];
		fetch(`${base_url}/feedback`, {
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

				var result = JSON.parse(chunks);
				var feedback = JSON.parse(result["feedback"]);

				var ratings = [0, 0, 0, 0, 0];

				for (var i = 0; i < feedback.length; i++) {
					ratings[feedback[i]['rating'] - 1] += 1;
					console.log(feedback[i]['rating']);
				}

				console.log(result);

				self.setState({ "totratings": ratings });
				self.setState({ "rating": parseInt(result["rating"]) });
				self.setState({ "comment": result["comment"] });


				// window.location.href = '/dashboard';
			});
		})

	}

	state = {
		"rating": 0,
		"comment": null,
		"totratings": null,
		"user_id": null
	}

	handleCommentChange = (e) => {
		console.log(this.state);
		this.setState({ "comment": e.target.value });
	}

	handleRatingChange = (e) => {
		this.setState({ "rating": e });
	}

	handleSubmit = () => {
		var flag = 1;
		if (this.state.comment === "") {
			flag = 0;
		}

		var base_url = cookie.load('base_url');

		var tobesent = {
			"rating": this.state.rating,
			"comment": this.state.comment,
			"user_id": cookie.load('user_id')
		}

		const chunks = [];
		fetch(`${base_url}/submitfeedback`, {
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

				var result = JSON.parse(chunks);
				// console.log(JSON.parse(result['feedback']));
				console.log(result);

				if(result["status"] == 200) {
					window.location.href = '/feedback';
				}
				
			});
		})



		if (flag) {
			alert("Feedback Submitted");
		} else {
			alert("Please give both comments and rating");
		}
	}


	render() {

		return (
			<div class="feedbackpage">

				{this.state.user_id !== null ? <div>

					<h1>Your Feedback is very Valuable</h1>
					<div class="feedbackform">

						<Form>
							<Form.Group controlId="rating" className="rating">

								{this.state.rating == 0 ? null :
									<div>
										<Form.Label class="formlabel">Rating</Form.Label>


										<MDBContainer>
											<ReactStars
												count={5}
												value={this.state.rating}
												onChange={this.handleRatingChange}
												size={50}
												activeColor="#ffd700"
											/>

											{/* <MDBRating
	iconFaces
	fillClassName='black-text'
	iconRegular
	// onChange={this.handleRatingChange}
	// onClick={this.handleRatingChange}
	// getValue={this.handleRatingChange}
	// value={this.state.rating}
	iconSize="2x"
/> */}
										</MDBContainer>
									</div>
								}


							</Form.Group>

							{this.state.comment == null ?

								<Form.Group controlId="comments">
									<Form.Label class="formlabel">Comments</Form.Label>
									<Form.Control as="textarea" placeholder="Enter your comments" rows="5"
										onChange={this.handleCommentChange} />
								</Form.Group> :

								<Form.Group controlId="comments">
									<Form.Label class="formlabel">Comments</Form.Label>
									<Form.Control as="textarea" placeholder="Enter your comments" rows="5" defaultValue={this.state.comment}
										onChange={this.handleCommentChange} />
								</Form.Group>

							}

							<Button variant="primary" onClick={this.handleSubmit}>
								Submit
                        </Button>
						</Form>
					</div>

					{
						this.state.totratings == null ? null : <div className="mychart">
							<Chart totratings={this.state.totratings} />
						</div>
					}

				</div> : <div>
						<h1>Please Login!!</h1>
					</div>
				}

			</div>

		);
	}
}

export default Feedback;