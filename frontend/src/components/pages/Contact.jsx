import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope, faPhone, faHouse, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";
import SectionDivider from "../ui/SectionDivider";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";

export default function Contact() {
    const pageTitle = "Contact";
    const contactImagePath = `${import.meta.env.BASE_URL}images/brand/contact.webp`;
    const initialFormData = { name: "", email: "", message: "" };
    const [formData, setFormData] = useState(initialFormData);
    const [submitted, setSubmitted] = useState(false);

    // Copies the previous state, then rewrites only the fields that changed.
    // [name] uses the input's name attribute as the object key, value is what user types in input
    function handleChange(event) {
        const { name, value } = event.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    }

    function handleSubmit(event) {
        event.preventDefault();

        setFormData(initialFormData);
        setSubmitted(true);
    }

    usePageTitleForBrowserTab(pageTitle);

    return (
        <main id="contact">
            <img src={contactImagePath} alt="" className="large-page-image" />
            <PageTitle title={pageTitle} />
            <h2>Don't be a stranger, leaf me a message!</h2>
            <div className="contact-page-summary">
                Have a question, suggestion, or a leafy idea to share? Send it my way. I'd love to
                hear from you!
            </div>
            <div className="contact-info-container">
                <div className="contact-info">
                    <FontAwesomeIcon icon={faEnvelope} />
                    <strong> Email:</strong> support@plant_thyme.com
                </div>
                <div className="contact-info">
                    <FontAwesomeIcon icon={faPhone} />
                    <strong> Phone:</strong> 610-867-5309
                </div>
                <div className="contact-info">
                    <span className="address-label">
                        <FontAwesomeIcon icon={faHouse} />
                        <strong> Address:</strong>
                    </span>
                    Plant Thyme
                    <br />
                    123 Greenhouse Lane
                    <br />
                    Sproutville, PA 19008
                </div>
            </div>
            <SectionDivider />
            <form onSubmit={handleSubmit}>
                <h2>
                    <FontAwesomeIcon icon={faPenToSquare} /> Drop Us a Note!
                </h2>
                <label htmlFor="name">
                    Name <span className="red-font">*</span>
                </label>
                <input
                    id="name"
                    type="text"
                    name="name"
                    placeholder="Full Name"
                    autoComplete="name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
                <label htmlFor="email">
                    Email <span className="red-font">*</span>
                </label>
                <input
                    id="email"
                    type="email"
                    name="email"
                    placeholder="Email"
                    autoComplete="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />
                <label htmlFor="message">
                    Message <span className="red-font">*</span>
                </label>
                <textarea
                    id="message"
                    name="message"
                    placeholder="Your message here..."
                    autoComplete="off"
                    rows="4"
                    maxLength="500"
                    value={formData.message}
                    onChange={handleChange}
                    required
                />
                <Button innerText="Submit" type="submit" className="contact-submit-btn" />
                {submitted && (
                    <p className="confirmation-form-submitted">
                        🥰<strong> Thanks! Your message has been sent. </strong>🌱
                    </p>
                )}
            </form>
        </main>
    );
}
