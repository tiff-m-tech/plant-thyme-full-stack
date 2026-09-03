import { altFromFileName } from "../../utils/altFromFileName";

// Default for fileName is used by newly selected preview images that do not pass a filename yet. Will replace in Unit 2 when backend is added.
export default function ProgressPictureCard({ src, date, fileName = "Progress Picture" }) {
    return (
        <div className="progress-picture-card">
            <div className="progress-picture-date">{date}</div>
            <img src={src} alt={altFromFileName(fileName)} className="progress-picture" />
        </div>
    );
}
