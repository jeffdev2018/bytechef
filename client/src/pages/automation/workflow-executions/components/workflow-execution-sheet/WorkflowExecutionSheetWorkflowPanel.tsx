import LoadingIcon from '@/components/LoadingIcon';
import {Badge} from '@/components/ui/badge';
import {SheetCloseButton, SheetHeader, SheetTitle} from '@/components/ui/sheet';
import ReadOnlyWorkflowEditor from '@/shared/components/read-only-workflow-editor/ReadOnlyWorkflowEditor';
import {WorkflowExecution} from '@/shared/middleware/automation/workflow/execution';
import {useGetWorkflowQuery} from '@/shared/queries/automation/workflows.queries';
import {useGetComponentDefinitionsQuery} from '@/shared/queries/platform/componentDefinitions.queries';

const WorkflowExecutionSheetWorkflowPanel = ({workflowExecution}: {workflowExecution: WorkflowExecution}) => {
    const {project, projectDeployment, workflow} = workflowExecution;

    const {data: workflowDetails} = useGetWorkflowQuery(workflow!.id!, !!workflow?.id);

    const workflowComponentNames = [
        ...(workflowDetails?.workflowTriggerComponentNames ?? []),
        ...(workflowDetails?.workflowTaskComponentNames ?? []),
    ];

    const {data: componentDefinitions} = useGetComponentDefinitionsQuery(
        {include: workflowComponentNames},
        workflowComponentNames !== undefined
    );

    return (
        <div className="flex size-full flex-col bg-muted/50">
            <SheetHeader className="flex flex-row items-center justify-between space-y-0 p-3">
                <SheetTitle>
                    <span>{project?.name}</span>

                    <span>/</span>

                    <span>{workflow?.label}</span>

                    <span>/</span>

                    <Badge variant="secondary">{projectDeployment?.environment}</Badge>
                </SheetTitle>

                <SheetCloseButton />
            </SheetHeader>

            {componentDefinitions && workflowDetails ? (
                <ReadOnlyWorkflowEditor componentDefinitions={componentDefinitions} workflow={workflowDetails} />
            ) : (
                <LoadingIcon />
            )}
        </div>
    );
};

export default WorkflowExecutionSheetWorkflowPanel;
