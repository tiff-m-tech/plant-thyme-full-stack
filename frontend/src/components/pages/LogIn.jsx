import { useState } from "react";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import { altFromFileName } from "../../utils/altFromFileName";
import { logoImagePath } from "../../data/constants";
import PageTitle from "../ui/PageTitle";
import Button from "../ui/Button";
import { useNavigate } from "react-router";

const HARDCODED_USERNAME = "tiffany";
const HARDCODED_PASSWORD = "123";

export default function LogIn({ setIsLoggedIn }) {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function handleSubmit(event) {
        event.preventDefault();

        if (username === HARDCODED_USERNAME && password === HARDCODED_PASSWORD) {
            setIsLoggedIn(true);
            navigate("/home");
        } else {
            setError("Incorrect username or password.");
        }
    }

    usePageTitleForBrowserTab("Log In");

    return (
        <main id="logIn">
            <img
                src={logoImagePath}
                alt={altFromFileName(logoImagePath)}
                className="large-page-image"
            />
            <PageTitle title="Log In" />
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username <span className="red-font">*</span>
                </label>
                <input
                    id="username"
                    type="text"
                    name="username"
                    placeholder="♙ Type Your username"
                    autoComplete="username"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                    required
                />
                <label htmlFor="password">
                    Password <span className="red-font">*</span>
                </label>
                <input
                    id="password"
                    type="password"
                    name="password"
                    placeholder="🔒︎ Type Your Password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                />
                {error && <p className="red-font">{error}</p>}
                <Button innerText="Login" type="submit" />
            </form>
            <div className="mock-credentials">
                <strong>Demo login</strong>
                <p>Username: tiffany</p>
                <p>Password: 123</p>
            </div>
        </main>
    );
}
