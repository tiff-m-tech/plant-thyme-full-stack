import { useState } from "react";
import { Link, useNavigate } from "react-router";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import { altFromFileName } from "../../utils/altFromFileName";
import { logoImagePath } from "../../constants";
import { registerUser, loginUser, saveToken } from "../../services/api";
import PageTitle from "../ui/PageTitle";
import Button from "../ui/Button";

export default function Register({ setIsLoggedIn }) {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [fieldErrors, setFieldErrors] = useState({}); // e.g. { email: "Please enter a valid email." }
    const [error, setError] = useState(""); // general errors, e.g. "Username is already taken."

    // Creates the account, then logs the new user in right away
    async function handleSubmit(event) {
        event.preventDefault();
        setFieldErrors({});
        setError("");

        // Front-end only check: the backend never sees confirmPassword
        if (password !== confirmPassword) {
            setFieldErrors({ confirmPassword: "Passwords do not match." });
            return;
        }

        try {
            await registerUser(username, email, password);
            const data = await loginUser(username, password);
            saveToken(data.token);
            setIsLoggedIn(true);
            navigate("/home");
        } catch (err) {
            if (err.fieldErrors) {
                setFieldErrors(err.fieldErrors); // 400: show each message under its input
            } else if (err.statusCode) {
                setError(err.message); // 409: "Username is already taken."
            } else {
                setError("Could not create account. Please try again.");
            }
        }
    }

    usePageTitleForBrowserTab("Sign Up");

    return (
        <main id="register">
            <img
                src={logoImagePath}
                alt={altFromFileName(logoImagePath)}
                className="large-page-image"
            />
            <PageTitle title="Sign Up" />
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username <span className="red-font">*</span>
                </label>
                <input
                    id="username"
                    type="text"
                    name="username"
                    placeholder="♙ Choose a username"
                    autoComplete="username"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                    required
                />
                {fieldErrors.username && <p className="red-font">{fieldErrors.username}</p>}

                <label htmlFor="email">
                    Email <span className="red-font">*</span>
                </label>
                <input
                    id="email"
                    type="email"
                    name="email"
                    placeholder="✉︎ Type your email"
                    autoComplete="email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                />
                {fieldErrors.email && <p className="red-font">{fieldErrors.email}</p>}

                <label htmlFor="password">
                    Password <span className="red-font">*</span>
                </label>
                <input
                    id="password"
                    type="password"
                    name="password"
                    placeholder="🔒︎ At least 8 characters"
                    autoComplete="new-password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                />
                {fieldErrors.password && <p className="red-font">{fieldErrors.password}</p>}

                <label htmlFor="confirmPassword">
                    Confirm Password <span className="red-font">*</span>
                </label>
                <input
                    id="confirmPassword"
                    type="password"
                    name="confirmPassword"
                    placeholder="🔒︎ Type it again"
                    autoComplete="new-password"
                    value={confirmPassword}
                    onChange={(event) => setConfirmPassword(event.target.value)}
                    required
                />
                {fieldErrors.confirmPassword && (
                    <p className="red-font">{fieldErrors.confirmPassword}</p>
                )}

                {error && <p className="red-font">{error}</p>}
                <Button innerText="Sign Up" type="submit" />
            </form>
            <p className="login-link">
                Already have an account? <Link to="/">Log in</Link>
            </p>
        </main>
    );
}
