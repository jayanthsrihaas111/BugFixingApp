import React, { Component } from 'react';
import { Form, Button } from 'react-bootstrap';
import bulb from "./miracle.png"
import RichTextEditor from 'react-rte';
import cookie from 'react-cookies';

import { MDBCarousel, MDBBox, MDBIcon } from 'mdbreact';

export class AskQuestion extends Component {

	state = {
		value: RichTextEditor.createValueFromString("<p><br></br><br></br><br></br></p>", 'html'),

		'wrongAnswerFormat': 0,
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

		fetch(`${base_url}/openpostanswer`, {
			method: 'POST',
			headers: {

			},
			body: JSON.stringify({ "Ques_id": cookie.load('Ques_id') })
		}).then(function (response) {


			const reader = response.body.getReader();

			reader.read().then(({ value, done }) => {
				var string = new TextDecoder("utf-8").decode(value);
				chunks.push(string);
			}).then(() => {
				result = JSON.parse(chunks);
				console.log(chunks);
			}).then(() => {

				self.setState({ question: result['question'] });
				self.setState({ creator: result['creator_id'] });
				self.setState({ question_desc: result['question_desc'] });

				// console.log(self.state);

			}
			);
		})

	}


	onQuestionChange = (value) => {
		this.setState({ value });
		if (this.props.onChange) {
			// Send the changes up to the parent component as an HTML string.
			// This is here to demonstrate using .toString() but in a real app it
			// would be better to avoid generating a string on each change.
			this.props.onChange(
				value.toString('html')
			);
		}
	};

	postQuestion = (e) => {
		var flag = 1;

		this.setState(
			{
				'wrongTitleFormat': 0,
				'wrongTagsFormat': 0,
				'wrongQuestionFormat': 0,
			}
		)

		if (this.state.value.toString('html') == "") {
			this.setState({ 'wrongAnswerFormat': 1 })
			flag = 0;
		}

		if (flag) {
			console.log(this.state.value.toString('html'));

			var base_url = cookie.load('base_url');

			var tobesent = {
				'answer_id': String(Math.floor(Math.random() * 929392372328 + 89219283)),
				'full_answer': this.state.value.toString('html'),
				'Ques_id' : cookie.load('Ques_id'),
				'user_id': cookie.load('user_id'),
				'acceptance_status' : 0,
				'like_count': 0,
			}

			const chunks = [];
			fetch(`${base_url}/postanswer`, {
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
					console.log(result);

					if (result['status'] === "200") {
						alert('Answer Posted');
						window.location.href = window.location.origin + '/dashboard';
					}
				});
			})



		} else {
			alert("Fill all Details");
		}
	}


	render() {
		const toolbarConfig = {
			// Optionally specify the groups to display (displayed in the order listed).
			display: ['INLINE_STYLE_BUTTONS', 'BLOCK_TYPE_BUTTONS', 'BLOCK_TYPE_DROPDOWN', 'HISTORY_BUTTONS'],
			INLINE_STYLE_BUTTONS: [
				{ label: 'Bold', style: 'BOLD', className: 'custom-css-class' },
				{ label: 'Italic', style: 'ITALIC' },
				{ label: 'Underline', style: 'UNDERLINE' }
			],
			BLOCK_TYPE_DROPDOWN: [
				{ label: 'Normal', style: 'unstyled' },
				{ label: 'Heading Large', style: 'header-one' },
				{ label: 'Heading Medium', style: 'header-two' },
				{ label: 'Heading Small', style: 'header-three' }
			],
			BLOCK_TYPE_BUTTONS: [
				{ label: 'UL', style: 'unordered-list-item' },
				{ label: 'OL', style: 'ordered-list-item' }
			]
		};
		return (
			<div>
				<MDBCarousel>
					<MDBBox>
						<div className="container-fluid" style={{ height: "100%" }}>

							<div id="left">
								{
									this.state.question === null ?
										<h1>Error Fetching question</h1> :
										<div className="card" style={{ marginLeft:"0px" , width: "90%" }}>
											<div className="card-header">
												{this.state.question}
											</div>
											<div className="card-body">
												<div dangerouslySetInnerHTML={{ __html: this.state.question_desc }} />
											</div>
										</div>

								}

							</div>

						</div>
					</MDBBox>
				</MDBCarousel>


				<div class="imgQues">
					<img src={bulb} alt="bulb"></img>


					<div class="askQues">
						<Form>


							<Form.Group controlId="exampleForm.ControlTextarea1">
								<Form.Label><b>Answer area</b></Form.Label>
								<RichTextEditor 
									toolbarConfig={toolbarConfig}
									value={this.state.value}
									onChange={this.onQuestionChange}
									className='custom-css-class'
								/>
							</Form.Group>

							<br></br>
							<Button variant="primary" onClick={this.postQuestion}>
								Post your Answer
			                        </Button>

						</Form>
					</div>
				</div>
			</div>

		);
	}
}


export default AskQuestion;