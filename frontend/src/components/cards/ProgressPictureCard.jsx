import { faBookOpen } from "@fortawesome/free-solid-svg-icons";
import Button from "../ui/Button";

export default function ProgressPictureCard({ src, date, onClick }) {
    return (
        <div className="progress-picture-card">
            <div className="progress-picture-date">{date}</div>
            <img
                src={src}
                alt={`Progress picture of plant from ${date}`}
                className="progress-picture"
            />
            <Button innerText="Progress Details" icon={faBookOpen} onClick={onClick} />
        </div>
    );
}
