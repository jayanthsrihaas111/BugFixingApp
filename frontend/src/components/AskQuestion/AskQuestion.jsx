import React, { Component } from 'react';
import './AskQuestion.scss';
import cookie from 'react-cookies';
import { Form, Button } from 'react-bootstrap';
import bulb from "./bestuu.png"
import RichTextEditor from 'react-rte';
import { MDBCarousel, MDBBox, MDBIcon } from 'mdbreact';

export class AskQuestion extends Component {

	state = {
		value: RichTextEditor.createValueFromString("<p><br></br><br></br><br></br></p>", 'html'),
		'title': "",
		'tags': "",
		'desc': "defaultdesc",
		'wrongTitleFormat': 0,
		'wrongTagsFormat': 0,
		'wrongQuestionFormat': 0,
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

	onTitleChange = (e) => {
		this.setState({ 'title': e.target.value })
	}
	onTagsChange = (e) => {
		this.setState({ 'tags': e.target.value })
	}

	postQuestion = (e) => {
		var flag = 1;

		this.setState(
			{
				'wrongTitleFormat': 0,
				'wrongTagsFormat': 0,
				'wrongQuestionFormat': 0,
			}
		)

		if (this.state.title === "") {
			this.setState({ 'wrongTitleFormat': 1 })
			flag = 0;
		}
		if (this.state.tags === "") {
			this.setState({ 'wrongTagsFormat': 1 })
			flag = 0;
		}
		if (this.state.value === "") {
			this.setState({ 'wrongQuestionFormat': 1 })
			flag = 0;
		}

		if (flag) {

			var base_url = cookie.load('base_url');

			var tobesent = {
				'Ques_id': String(Math.floor(Math.random() * 929392372328 + 89219283)),
				'Question_Title': this.state.title,
				'Question_Desc': this.state.value.toString('html'),
				'Creator_id': cookie.load('user_id'),
				'Viewcount': 0,
				'Answer_count': 0,
				'Votes': 0,
				'Tags': this.state.tags,
			}

			const chunks = [];
			fetch(`${base_url}/askquestion`, {
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
						alert('Question Posted');
						window.location.href = window.location.origin + '/dashboard';
					}
				});
			})
		} else {
			alert("Fill all Details");
		}
		console.log(this.state);
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
			<div class="imgQues">



				<img src={bulb} alt="bulb"></img>

				<div class="askQues">
					<Form>
						<Form.Group controlId="exampleForm.ControlInput1">
							<Form.Label><b>Question Title</b></Form.Label>
							<Form.Control type="text" placeholder="Enter the title here" onChange={this.onTitleChange} />
						</Form.Group>

						<Form.Group controlId="exampleForm.ControlTextarea1">
							<Form.Label><b>Question Description</b></Form.Label>
							<RichTextEditor
								toolbarConfig={toolbarConfig}
								value={this.state.value}
								onChange={this.onQuestionChange}
								className='custom-css-class'
							/>
						</Form.Group>


						<Form.Group controlId="exampleForm.ControlInput1">
							<Form.Label>Tags</Form.Label>
							<Form.Control type="email" placeholder="Your tags go here" onChange={this.onTagsChange} />
						</Form.Group>
						<br></br>
						<Button variant="primary" onClick={this.postQuestion}>
							Post your Question
                        </Button>

					</Form>
				</div>
			</div>

		);
	}
}


export default AskQuestion;